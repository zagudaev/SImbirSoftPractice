package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.example.SimbirSoftPractice.domain.modelDTO.*;
import ru.example.SimbirSoftPractice.domain.model.*;
import ru.example.SimbirSoftPractice.mappers.MessageMapper;
import ru.example.SimbirSoftPractice.repository.MenDao;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.util.YoutubeApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

@Service
@AllArgsConstructor
public class BotServiceImpl implements BotService {
   private final RoomService roomService;
    private final MenService menService;
    private final RoomDao roomDao;
    private final MenDao menDao;
    //private final MessageMapper messageMapper;


    @Override
    @PreAuthorize("#messageServiceImpl.findById(messageDTO.id).man.ban == false ") //TODO в spel-выражения я не уверен
    public MessageDTO messageАnalysis(MessageDTO messageDTO) {
        Message message = MessageMapper.INSTANCE.toMessage(messageDTO);
        String[] command = message.getTextMessage().split(" ");
        YoutubeApi youtubeApi = new YoutubeApi();
        Message requestMessage = new Message();
        String textRequestMessege = "";
        String channelName;
        String videoName;
        String videoId = "";
        String viewCount = "";
        String likeCount = "";
        final String url = "https://www.youtube.com/watch?v=";
        int i = 0;
        switch (command[i]) {
            case "//room":
                i++;
                switch (command[i]) {
                    case "create":
                        i++;
                        if (i == command.length) {
                            RoomDTO roomDTO = new RoomDTO();
                            roomDTO.setCreator(messageDTO.getMen());
                            roomDTO.setName(command[i]);
                            roomDTO.setPrivat(false);
                            roomService.save(roomDTO);
                            textRequestMessege += "ok";
                        } else if (command[++i].equals("-c")) {
                            RoomDTO roomDTO = new RoomDTO();
                            roomDTO.setCreator(messageDTO.getMen());
                            roomDTO.setName(command[i]);
                            roomDTO.setPrivat(true);
                            roomService.save(roomDTO);
                            textRequestMessege += "ok";
                        } else {
                            textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";
                        }

                        break;
                    case "remove":
                        i++;
                        Long id = roomDao.findByName(command[i]).get().getId();
                        roomService.delete(id);
                        textRequestMessege += "ok";
                        break;
                    case "rename":     //room rename {Текущее название комнаты} {Новое название комнаты} - переименование комнаты (владелец и админ);
                        i++;                                                            // переделал команду

                        Room room = roomDao.findByName(command[i]).get();
                        room.setName(command[++i]);
                        roomService.commandUpdate(room);
                        textRequestMessege += "ok";
                        break;
                    case "connect":
                        i++;
                        if (i == command.length) {
                            RoomDTO roomDTO = null;
                            roomDTO.setName(command[i]);
                            MenDTO menDTO = null;
                            menDTO.setLogin(menDao.findById(messageDTO.getMen()).get().getLogin());
                            roomService.addUser(roomDTO, menDTO);
                            textRequestMessege += "ok";
                        } else if (command[i++].equals("-l")) {
                            RoomDTO roomDTO = null;
                            roomDTO.setName(command[i]);
                            MenDTO menDTO = null;
                            menDTO.setLogin(command[++i]);
                            roomService.addUser(roomDTO, menDTO);
                            textRequestMessege += "ok";
                        } else {
                            textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";
                        }
                        break;
                    case "disconnect":
                        i++;
                        if (i == command.length) {
                            RoomDTO roomDTO = null;
                            roomDTO.setName(command[i]);
                            MenDTO menDTO = null;
                            menDTO.setLogin(menDao.findById(messageDTO.getMen()).get().getLogin());
                            roomService.deleteUser(roomDTO, menDTO);
                            textRequestMessege += "ok";
                        } else if (command[i++].equals("-l")) {
                            RoomDTO roomDTO = null;
                            int roomNameIndex = i - 1;
                            roomDTO.setName(command[i]);
                            MenDTO menDTO = null;
                            menDTO.setLogin(command[++i]);
                            roomService.deleteUser(roomDTO, menDTO);
                            textRequestMessege += "ok";
                        } else {
                            textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";
                        }
                        break;
                    default:
                        textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";
                }
                break;
            case "//user":
                i++;
                switch (command[i]) {
                    case "rename":       //user rename {login пользователя} {Новый username} // переделал команду
                        MenDTO menDTO = null;
                        menDTO.setLogin(menDao.findByLogin(command[++i]).get().getLogin());
                        i++;
                        String newUserName = command[i];
                        menService.commandRename(menDTO, newUserName);
                        textRequestMessege += "ok";
                        break;
                    case "ban":         //user ban {login пользователя}// переделал команду
                        i++;
                        String loginban = command[i];
                        MenDTO manformban = null;
                        manformban.setLogin(menDao.findByLogin(loginban).get().getLogin());
                        menService.ban(manformban);
                        if (i != command.length){
                            i++;
                            if(command[++i].equals("-l")){
                                List<Room> roomList = roomDao.findAll();
                                int idUserBan = Math.toIntExact(menDao.findByLogin(loginban).get().getId());
                                for (Room room : roomList){
                                    List<Men> menList = room.getMen();
                                    for (Men men : menList){
                                        if (men.getId() == idUserBan){
                                            roomService.deleteUserComand(room, men);
                                        }
                                    }
                                }
                            }
                        }
                        textRequestMessege += "ok";
                        break;
                    case "unban":       //user unban {login пользователя}// добавил команду
                        i++;
                        String loginunban = command[i];
                        MenDTO manformunban = null;
                        manformunban.setLogin(menDao.findByLogin(loginunban).get().getLogin());
                        menService.unBan(manformunban);
                        textRequestMessege += "ok";
                        break;
                    case "moderator":       //user rename {login пользователя} {Новый username} // переделал команду
                        i++;
                        String loginmoderator = command[i];
                        i++;
                        MenDTO manformmoderator = null;
                        if (command[i].equals("-n")) {
                            manformmoderator.setLogin(menDao.findByLogin(loginmoderator).get().getLogin());
                            menService.addModerator(manformmoderator);
                            textRequestMessege += "ok";
                        } else if (command[i].equals("-d")) {
                            manformmoderator.setLogin(menDao.findByLogin(loginmoderator).get().getLogin());
                            menService.deleteModerator(manformmoderator);
                            textRequestMessege += "ok";
                        } else {
                            textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";
                        }
                        break;
                }

                break;
            case "//yBoy":
                  i++;
                switch (command[i]) {
                    case "find":
                      i++;
                      if (command[i].equals("-k")){
                          i++;
                          if (command[i].equals("-l")){
                              i++;
                              channelName = command[i];
                              if (command[++i].equals("||")){
                                  i++;
                                  videoName = command[i];
                                  List<String> list = youtubeApi.GetVideo(channelName,videoName);
                                  if (i == command.length){
                                      videoId = list.get(0);

                                  }else if(command[++i].equals("-v")){
                                      viewCount = list.get(1);
                                      if (i != command.length){
                                          i++;
                                          if(command[++i].equals("-l")){
                                              likeCount = list.get(2);
                                          }
                                      }
                                  }else if(command[++i].equals("-l")){
                                      likeCount = list.get(2);
                                      if (i != command.length){
                                          if(command[++i].equals("-v")){
                                              viewCount = list.get(1);
                                          }
                                      }
                                } else {textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";}

                              }else {textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";}

                          }else{textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";}

                      }else {textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";}//help

                      textRequestMessege += url + videoId + "/n" + "Кол-во просмотров" + viewCount + "/n" + "Кол-во лайков" + likeCount;
                      break;
                    case "changelInfo" :
                      i++;
                      List<String> list = youtubeApi.GetChangellInfo(command[i]);
                      textRequestMessege += "Имя канала" + list.get(0) + "/n"
                              + list.get(1) + "/n"
                              + list.get(2) + "/n"
                              + list.get(3) + "/n"
                              + list.get(4) + "/n"
                              + list.get(5) + "/n";
                              break;
                    case "vidoCommentRanom" :
                        i++;
                        channelName = command[i];
                        if (command[++i].equals("||")){
                            i++;
                            videoName = command[i];
                            List<String> comment = youtubeApi.GetvidoCommentRanom(channelName,videoName);
                            textRequestMessege += comment.get(0) + "/n" + comment.get(1) ;
                        } else {textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";}
                        break;
                    default:
                        textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";
                }


                break;
            case "//help":
                if (i == command.length) {
                    try {
                        textRequestMessege += RemoveCommands();
                    } catch (Exception e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка чтения файла");
                    }
                }
                break;
            default:
                textRequestMessege +="Ошибка  команды. Посмотреть все команды //help";
        }

        requestMessage.setTextMessage(textRequestMessege);
        requestMessage.setRoom(roomDao.findByName("BOT").get());

        return MessageMapper.INSTANCE.toMessageDTO(requestMessage);
    }

    public String RemoveCommands() throws Exception{
        URL url = getClass().getResource("commands.txt");
        File file = new File(url.getPath());
        BufferedReader bufferedReader  = new BufferedReader(new FileReader(file));

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return line;

    }
}
