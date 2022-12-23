package fingerorder.webapp.annotation;

import java.lang.reflect.Field;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class TrimEntityListener {

    @PrePersist
    @PreUpdate
    public void postLoad(Object entity) {

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Trim.class)) {
                field.setAccessible(true);
            }
            try {
                Object value = field.get(entity);
                if (value instanceof String) {
                    String string = (String) value;
                    field.set(entity, string.strip());
                }
            } catch (IllegalAccessException e) {

            }
        }
    }
}
