package pl.com.altar.dddlayerd.application.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class BaseEvent {
    private String message;
    private Object target;
    public String eventType() {
        return getClass().getName();
    }
}
