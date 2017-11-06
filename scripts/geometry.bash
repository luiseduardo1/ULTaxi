#! /usr/bin/env bash

declare -xr is_geometry_file_loaded=true

terminal_columns_number() {
    tput cols
}

terminal_lines_number() {
    tput lines
}

save_cursor_position() {
    tput sc
}

restore_cursor_position() {
    tput rc
}

move_cursor_to() {
    local -ri _x=${1}
    local -ri _y=${2}
    tput cup ${_x} ${_y}
}

move_left() {
    local -ri _characters_number=${1}
    tput cub ${_characters_number}
}

move_right() {
    local -ri _characters_number=${1}
    tput cuf ${_characters_number}
}

move_left_one_space() {
    tput cub1
}

move_right_one_space() {
    tput cuf1
}

move_to_last_line_first_column() {
    tput ll
}

move_up_one_line() {
    tput cuu1
}

erase_characters() {
    local -ri _characters_number=${1}
    tput ech ${_characters_number}
}

clear_screen_and_home_cursor() {
    tput clear
}

clear_to_beginning_of_line() {
    tput el1
}

clear_to_end_of_line() {
    tput el
}

clear_to_end_of_screen() {
    tput ed
}

insert_characters() {
    local -r _characters="${1}"
    tput ich "${_characters}"
}

insert_lines() {
    local -ri _lines_number=${1}
    tput il ${_lines_number}
}
