package com.postgresql.indiegogo.commands;

import com.postgresql.indiegogo.ApiResponse;

public interface Command {
    ApiResponse execute();
}
