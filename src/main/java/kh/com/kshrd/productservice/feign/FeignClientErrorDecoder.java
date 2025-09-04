package kh.com.kshrd.productservice.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import kh.com.kshrd.productservice.exception.BadRequestException;
import kh.com.kshrd.productservice.exception.ConflictException;
import kh.com.kshrd.productservice.exception.NotFoundException;
import kh.com.kshrd.productservice.exception.UnauthorizeException;

import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class FeignClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        String msg = "Unknown error";
        try {
            String body = readBody(response);
            if (body != null && !body.isBlank()) {
                JsonNode node = mapper.readTree(body);
                if (node.has("detail")) {
                    msg = node.get("detail").asText();
                } else {
                    msg = body;
                }
            }
        } catch (Exception e) {
            msg = "Error reading response body";
        }

        return switch (response.status()) {
            case 400 -> new BadRequestException(msg);
            case 401 -> new UnauthorizeException(msg);
            case 404 -> new NotFoundException(msg);
            case 409 -> new ConflictException(msg);
            default -> new RuntimeException(msg);
        };
    }

    private static String readBody(Response response) {
        if (response.body() == null) return null;
        try (Reader reader = response.body().asReader(StandardCharsets.UTF_8)) {
            String s = Util.toString(reader);
            return s.length() > 8192 ? s.substring(0, 8192) + "…(truncated)" : s;
        } catch (Exception ignored) {
            return null;
        }
    }
}
