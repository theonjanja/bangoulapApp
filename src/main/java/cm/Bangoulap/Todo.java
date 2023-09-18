package cm.Bangoulap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Todo {
    private Long id;
    private String title;
    private Boolean completed;
    private Long userId;

    public String toString() {
        return "Value: {id: " + id +",\ntitle: " + title + ",\ncompleted: " + completed + ",\nuserId: " + userId + "\n}";
    }
}
