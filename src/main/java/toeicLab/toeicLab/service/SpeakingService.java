package toeicLab.toeicLab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakingService {

    /**
     * 사용자가 파트1의 대한 답 중 중복되는것을 제외하고 배열에 저장
     * @param arrSpeech 사용자의 답을 띄어쓰기 마다 스플래쉬해서 저장된 배열
     * @param userContent 사용자의 답 중 연속된것을 제외하고 저장되는 리스트
     * @param strContent 문제의 내용 중 연속된것을 제외하고 저장되는 리스트
     * @param arr 문제의 내용을 스플래쉬해서 저장된 배열
     */
    public void speechPart1(String[] arrSpeech, List<String> userContent, List<String> strContent, String[] arr) {
        for (String value : arr) {
            if (!strContent.contains(value)) {
                strContent.add(value);
            }
        }
        for (String userValue : arrSpeech){
            if (!userContent.contains(userValue)) {
                userContent.add(userValue);
            }
        }
    }

    /**
     * 사용자가 파트2의 대한 답 중 중복되는것을 제외하고 배열에 저장
     * @param arrSpeech 사용자의 답을 띄어쓰기 마다 스플래쉬해서 저장된 배열
     * @param userContent 사용자의 답 중 연속된것을 제외하고 저장되는 리스트
     */
    public void speechPart2(String[] arrSpeech, List<String> userContent) {
        for (String userValue : arrSpeech){
            if (!userContent.contains(userValue)) {
                userContent.add(userValue);
            }
        }
    }
}
