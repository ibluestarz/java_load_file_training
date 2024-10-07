package fr.lernejo.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class CatTest {


    @Test
    void add_2_and_2_should_return_4() throws FileNotFoundException {
        String[] arr = new String[0];
        int result = Cat.run(arr);

        Assertions.assertThat(result)
            .isEqualTo(3);
    }
}
