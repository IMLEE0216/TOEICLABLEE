package toeicLab.toeicLab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakingService {

    public void speechPart1(String[] arrSpeech, List<String> userContent, double count, List<String> strContent, String[] arr) {
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

        for (int i = 0; i < strContent.size(); ++i){
            for (String s : userContent) {
                if (strContent.get(i).equals(s)) {
                    count += 1;
                }
            }
        }
    }

    public void speechPart2(String[] arrSpeech, List<String> userContent, double count, List<String> str) {
        for (String userValue : arrSpeech){
            if (!userContent.contains(userValue)) {
                userContent.add(userValue);
            }
        }
        for (int j = 0; j < userContent.size(); ++j) {
            for (int i = 0; i < str.size(); ++i) {
                if (userContent.get(j).equals(str.get(i))) {
                    count += 1;
                }
            }
        }
    }
}
