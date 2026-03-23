package com.ti9.ti9_backend.security.records;

import com.ti9.ti9_backend.security.enums.EnumUserRole;

public record RegisterDto (String login, String password, EnumUserRole role, Boolean enabled){
}
