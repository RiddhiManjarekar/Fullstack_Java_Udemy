package org.studyeasy.car;
import org.studyeasy.interfaces.Car;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.studyeasy.interfaces.Car;

@Component
@Primary
public class Swift implements Car {
	@Override
    public void specs() {
        System.out.println("Hatchback from Suzuki");
    }
}