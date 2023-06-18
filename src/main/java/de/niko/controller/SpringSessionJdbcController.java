package de.niko.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.Session;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class SpringSessionJdbcController {
    private final JdbcIndexedSessionRepository jdbcIndexedSessionRepository;

    public SpringSessionJdbcController(JdbcIndexedSessionRepository jdbcIndexedSessionRepository) {
        this.jdbcIndexedSessionRepository = jdbcIndexedSessionRepository;
    }

    @GetMapping("/")
    public ResponseEntity<Object> root() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        var request = servletRequestAttributes.getRequest();
        var session = request.getSession();
        var sessionId = session.getId();

        session.setAttribute("TestAttr", "Some value");

        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }

    @GetMapping("/session")
    public ResponseEntity<Object> getFromSession(@RequestParam(required = false) String externalSessionId) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        var request = servletRequestAttributes.getRequest();
        var session = request.getSession();
        var sessionId = session.getId();

        assert !sessionId.equals(externalSessionId);

        Session externalSession = jdbcIndexedSessionRepository.findById(externalSessionId);
        if (externalSession != null) {
            var testAttr = externalSession.getAttribute("TestAttr");

            return new ResponseEntity<>(testAttr, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not fount :(", HttpStatus.NOT_FOUND);
        }
    }
}