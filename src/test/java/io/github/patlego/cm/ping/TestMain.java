package io.github.patlego.cm.ping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMain {

    @Test
    public void testCliParams() {
        Main.getArgs(new String[] {"--location", "/home/file.json"});

        Assertions.assertThrows(IllegalArgumentException.class, () -> Main.getArgs(new String[] {}));

        Assertions.assertThrows(IllegalArgumentException.class, () -> Main.getArgs(new String[] {"--myspacerocks", "pat_was_here"}));
    }
}
