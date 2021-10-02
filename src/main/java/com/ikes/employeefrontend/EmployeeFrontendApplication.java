package com.ikes.employeefrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class EmployeeFrontendApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeFrontendApplication.class, args);
	}

	@Bean
  public RouterFunction<ServerResponse> webappRouter() {
	  return RouterFunctions
      .resources("/**", new ClassPathResource("/"));
  }

  @Component
  public class RouteToIndex implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
      if (exchange.getRequest().getURI().getPath().equals("/")) {
        return chain.filter(
          exchange.mutate()
            .request(
              exchange.getRequest().mutate().path("/index.html").build()).build());
      }

      return chain.filter(exchange);
    }
  }
}
