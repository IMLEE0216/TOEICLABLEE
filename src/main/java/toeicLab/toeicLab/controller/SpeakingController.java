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
import toeicLab.toeicLab.repository.QuestionRepository;
import toeicLab.toeicLab.service.SpeechService;
import toeicLab.toeicLab.user.CurrentUser;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SpeakingController {

    private final SpeechService speechService2;
    private final QuestionRepository questionRepository;

    /**
     * [ToeicLab]의 SPK문제풀이 페이지로 이동합니다.
     * @param member
     * @param model
     * @return speaking/spk_sheet
     */
    @GetMapping("/spk_sheet")
    public String spkSheet(@CurrentUser Member member, Model model) {
        model.addAttribute("member", member);
        return "speaking/spk_sheet";
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
        return "speaking/spk_confirm_answer";
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
        return "speaking/schedule";
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

    @PostMapping("/result")
    public String resultSpeaking(Member member) {

        return "redirect:/";
    }
}
