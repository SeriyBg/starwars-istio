package demo.istio.starwars.ui;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class UIController {

    private static final String USER_AGENT = "user-agent";
    private static final String[] TRACE_HEADERS = new String[] {"x-request-id", "x-b3-traceid", "x-b3-spanid", "x-b3-parentspanid", "x-b3-sampled", "x-b3-flags", "x-ot-span-context"};


    private final QuoteService quoteService;

    @Autowired
    public UIController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/quote")
    public String quote(HttpServletRequest request, Map<String, Object> model) {
        model.put("quote", quoteService.quote(getForwardHeaders(request)));
        return "quote";
    }

    private HttpHeaders getForwardHeaders(HttpServletRequest request) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.add(USER_AGENT, request.getHeader(USER_AGENT));
        for (var traceHeader : TRACE_HEADERS) {
            var header = request.getHeader(traceHeader);
            if (header == null || header.isEmpty()) {
                continue;
            }
            log.info("Header - " + traceHeader + ": " + header);
            httpHeaders.add(traceHeader, header);
        }
        return httpHeaders;
    }
}
