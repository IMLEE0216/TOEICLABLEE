package toeicLab.toeicLab.domain;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@DiscriminatorValue("SPK")
public class SPK extends Question{

    private String recording;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ElementCollection
    private List<String> keyword = new ArrayList<>();
}
