package com.revs.jwtsecurity.demo;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@Tag(name = "Management Resource")
public class ManagementController {

    @Operation(
            description = "Get endpoint for manager",
            summary = "This is a summary for management get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("GET :: management controller");
    }

    @Hidden
    @PostMapping
    public ResponseEntity<String> post() {
        return ResponseEntity.ok("POST :: management controller");
    }

    @Hidden
    @PutMapping
    public ResponseEntity<String> put() {
        return ResponseEntity.ok("PUT :: management controller");
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("DELETE :: management controller");
    }
}
