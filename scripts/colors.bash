#! /usr/bin/env bash

declare -xr is_colors_file_loaded=true

declare -xri black=0
declare -xri red=1
declare -xri green=2
declare -xri yellow=3
declare -xri blue=4
declare -xri magenta=5
declare -xri cyan=6
declare -xri white=7
declare -xri reset=9

set_message_color_to() {
    local -ri _color=${1}
    local -r _message="${2}"
    set_font_color_to ${_color}
    echo "${_message}"
    reset_to_default
}

set_underline_to() {
    local -r _message="${1}"
    enter_underline
    echo ${2} "${_message}"
    exit_underline
}

add_font_color() {
    local -ri _color=${1}
    local -r _function=${2}
    shift 2
    set_font_color_to ${_color}
    ${_function} "${@}"
    reset_to_default
}

add_underline() {
    local -r _function=${1}
    shift 1
    enter_underline
    ${_function} "${@}"
    exit_underline
}

add_bold() {
    local -r _function=${1}
    shift 1
    set_bold
    ${_function} "${@}"
    reset_to_default
}

set_font_color_to() {
    local -ri _color=${1}
    tput setaf ${_color}
}

set_background_color_to() {
    local -ri _color=${1}
    tput setab ${_color}
}

reset_to_default() {
    tput sgr0
}

set_bold() {
    tput bold
}

set_half_bright() {
    tput dim
}

enter_underline() {
    tput smul
}

exit_underline() {
    tput rmul
}

enter_standout() {
    tput smso
}

exit_standout() {
    tput rmso
}
