package org.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Controller("/")
@Validated
@RequiredArgsConstructor
public class HelloController {

    private final HelperService svc;
    private final HelperService2 svc2;

    private final List<Api> apis;

    @Get(uri = "/hello/{name}", produces = MediaType.TEXT_PLAIN)
    public Single<String> hello(final @NotBlank String name) {
        return Single.just("Hello " + name + "!" + svc.output() + " " + svc2.output() + " ?? " + apis.size());
    }
}