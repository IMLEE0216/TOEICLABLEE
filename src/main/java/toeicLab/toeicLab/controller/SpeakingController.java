package toeicLab.toeicLab.controller;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toeicLab.toeicLab.domain.Member;
import toeicLab.toeicLab.domain.Question;
import toeicLab.toeicLab.domain.QuestionType;
import toeicLab.toeicLab.domain.SPK;
import toeicLab.toeicLab.repository.QuestionRepository;
import toeicLab.toeicLab.repository.SPKRepository;
import toeicLab.toeicLab.service.SpeakingService;
import toeicLab.toeicLab.service.SpeechService;
import toeicLab.toeicLab.user.CurrentUser;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SpeakingController {

    private final QuestionRepository questionRepository;
    private final SPKRepository spkRepository;
    private final SpeakingService speakingService;

    @GetMapping("/spk_select")
    public String spkSelect(Member member, Model model){
        model.addAttribute(member);
        return "speaking/spk_select";
    }
    
    @RequestMapping("/speech/{part}")
    @Transactional
    public String selectPart(Member member, @PathVariable String part, Model model){
        model.addAttribute(member);
        Question question = null;
        List<Question> list;
        if(part.equals("part1")){
            list = new ArrayList<>(questionRepository.findAllByQuestionType(QuestionType.SPK_PART1));
            question = list.get((int)Math.random()*list.size());
            model.addAttribute("question", question);
        } else if (part.equals("part2")){
            list = new ArrayList<>(questionRepository.findAllByQuestionType(QuestionType.SPK_PART2));
            question = list.get((int)Math.random()*list.size());
            model.addAttribute("question", question);
        }
        return "speaking/spk_part";
    }

    @GetMapping("/speech")
    @ResponseBody
    public String speechTest(Member member, Model model) throws Exception {
        JsonObject jsonObject = new JsonObject();
        StringBuffer sb = new StringBuffer();
        SpeechService.streamingMicRecognize(sb);
        jsonObject.addProperty("result",sb.toString());
        return jsonObject.toString();
    }

    @PostMapping("/speech_result")
    public String resultSpeaking(@CurrentUser Member member, @RequestParam("id") long id, @RequestParam("speech") String speech, Model model) {
        SPK spk = spkRepository.getOne(id);
        String realSpeech = speech.replaceAll("\"", "");
        String strSpeech = speech.replaceAll("\"", "").replaceAll("\\.","").toLowerCase(Locale.ROOT);
        String [] arrSpeech = strSpeech.split(" ");
        List<String> userContent = new ArrayList<>();
        double count = 0;
        double size = 0;
        double avg;
        if (spk.getQuestionType().equals(QuestionType.SPK_PART1)){
            String str = spk.getContent().replaceAll("\\.", "").replaceAll(",", "").toLowerCase(Locale.ROOT);
            List<String> strContent = new ArrayList<>();
            String [] arr = str.split(" ");
            speakingService.speechPart1(arrSpeech, userContent, strContent, arr);
            for (int i = 0; i < strContent.size(); ++i){
                for (String s : userContent) {
                    if (strContent.get(i).equals(s)) {
                        count += 1;
                    }
                }
            }
            size = arr.length;

        } else if (spk.getQuestionType().equals(QuestionType.SPK_PART2)){
            List<String> str = new ArrayList<>(spk.getKeyword());
            speakingService.speechPart2(arrSpeech, userContent, str);
            for (int j = 0; j < userContent.size(); ++j) {
                for (int i = 0; i < str.size(); ++i) {
                    if (userContent.get(j).equals(str.get(i))) {
                        count += 1;
                    }
                }
            }
            size = str.size();
        }

        avg = (count/size)*100;
        String result = String.format("%.2f", avg) + "%";
        model.addAttribute("average", result);
        model.addAttribute("myAnswer", realSpeech);
        model.addAttribute("question", spk);
        model.addAttribute(member);
        return "speaking/spk_answer_sheet";
    }




    /**
     * 일정 페이지로 이동합니다.
     * @param member
     * @param model
     * @return speaking/schedule
     */
    @GetMapping("/schedule")
    public String schedule(@CurrentUser Member member, Model model) {
        model.addAttribute("member", member);
        return "speaking/un_schedule";
    }

    /**
     * [ToeicLab]의 소개페이지로 이동합니다.
     * @return speaking/toeiclab_intro
     */
    @GetMapping("/toeiclab_introduction")
    public String toeiclabIntroduction() {
        return "speaking/toeiclab_intro";
    }
}
