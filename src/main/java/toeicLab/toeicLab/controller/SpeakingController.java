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
import toeicLab.toeicLab.service.SpeechService;
import toeicLab.toeicLab.user.CurrentUser;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SpeakingController {

    private final SpeechService speechService2;
    private final QuestionRepository questionRepository;
    private final SPKRepository spkRepository;

    /**
     * [ToeicLab]의 SPK문제풀이 페이지로 이동합니다.
     * @param member
     * @param model
     * @return speaking/spk_sheet
     */
    @GetMapping("/spk_sheet")
    public String spkSheet(@CurrentUser Member member, Model model) {
        model.addAttribute("member", member);
        return "spk_sheet_un";
    }

    /**
     * [ToeicLab]의 SPK문제확인 페이지로 이동합니다.
     * @param member
     * @param model
     * @return speaking/spk_confirm_answer
     */
    @GetMapping("/spk_confirm_answer")
    public String spkConfirmAnswer(@CurrentUser Member member, Model model) {
        model.addAttribute("member", member);
        return "un_spk_confirm_answer";
    }

    /**
     * SPK정답 페이지로 이동합니다.
     * @param member
     * @param model
     * @return speaking/spk_answer_sheet
     */
    @GetMapping("/spk_answer_sheet")
    public String spkAnswerSheet(@CurrentUser Member member, Model model) {
        model.addAttribute("member", member);
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
        return "un_schedule";
    }

    /**
     * [ToeicLab]의 소개페이지로 이동합니다.
     * @return speaking/toeiclab_intro
     */
    @GetMapping("/toeiclab_introduction")
    public String toeiclabIntroduction() {
        return "speaking/toeiclab_intro";
    }





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
    public String resultSpeaking(Member member, @RequestParam("id") long id, @RequestParam("speech") String speech, Model model) {
        SPK spk = spkRepository.getOne(id);
        String strSpeech = speech.replaceAll("\"", "");
        String [] arrSpeech = strSpeech.split(" ");
        int count = 0;
        double avg = 0;
        if (spk.getQuestionType().equals(QuestionType.SPK_PART1)){
            String str = spk.getContent();
            String [] arr = str.split(" ");
            List<String> strContent = new ArrayList<>();
            for (String value : arr) {
                if (!strContent.contains(value)) {
                    strContent.add(value);
                }
            }
            for (int i =0; i < strContent.size(); ++i){
                for (String s : arrSpeech) {
                    if (strContent.get(i).equals(s)) {
                        ++count;
                    }
                }
            }
//            avg = (count/arr.length)*100;
            System.out.println(count);
        }

        else if (spk.getQuestionType().equals(QuestionType.SPK_PART2)){
            List<String> str = new ArrayList<>(spk.getKeyword());
            System.out.println(Arrays.toString(arrSpeech));

            for (int j =0; j < arrSpeech.length; ++j) {
                for (int i = 0; i < str.size(); ++i) {
                    if (arrSpeech[j].equals(str.get(i))) {
                        ++count;
                    }
                }
            }
//            avg = (count/ str.size())*100;
            System.out.println(count);
        }


        model.addAttribute("average", avg);
        model.addAttribute("myAnswer", speech);
        model.addAttribute("question", spk);
        model.addAttribute(member);
        return "speaking/spk_answer_sheet";
    }
}
