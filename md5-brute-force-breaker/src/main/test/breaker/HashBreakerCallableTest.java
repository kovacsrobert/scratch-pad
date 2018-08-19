package breaker;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashBreakerCallableTest {

    @Test
    public void shouldSolveFor_gg() throws Exception {
        HashBreakerCallable hashBreakerCallable = new HashBreakerCallable(2, "73c18c59a39b18382081ec00bb456d43");
        assertThat(hashBreakerCallable.call()).isEqualTo("gg");
    }

    @Test
    public void shouldFailFor_aaa() throws Exception {
        HashBreakerCallable hashBreakerCallable = new HashBreakerCallable(2, "47bce5c74f589f4867dbd57e9ca9f808");
        assertThat(hashBreakerCallable.call()).isBlank();
    }
}