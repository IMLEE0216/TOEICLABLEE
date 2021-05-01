package toeicLab.toeicLab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakingService {

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

    public void speechPart2(String[] arrSpeech, List<String> userContent, List<String> str) {
        for (String userValue : arrSpeech){
            if (!userContent.contains(userValue)) {
                userContent.add(userValue);
            }
        }
    }
}
