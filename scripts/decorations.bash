#! /usr/bin/env bash

[ -z ${is_colors_file_loaded} ] && source "colors.bash"
[ -z ${is_geometry_file_loaded} ] && source "geometry.bash"
[ -z ${is_math_file_loaded} ] && source "math.bash"

declare -xr is_decorations_file_loaded=true

decorate_test_name() {
    local -r _name="${1}"

    echo
    add_bold add_font_color ${cyan} add_underline echo "-n" "Executing test:"
    add_bold add_font_color ${cyan} echo " ${_name}"
}

handle_test_result() {
    local _message_color=${green}
    local _status="SUCCESS:"
    local _message="Test passed!"
    local -ri _exit_code=${1}

    if [ ${_exit_code} -ne 0 ]; then
        _message_color=${red}
        _status="FAILURE:"
        _message="Test failed with exit code: ${_exit_code}."
    fi

    add_bold add_font_color ${_message_color} add_underline echo "-n" "${_status}"
    add_bold add_font_color ${_message_color} echo " ${_message}"
    return ${_exit_code}
}

display_preconditions() {
    display_conditions "Preconditions:" "$@"
}

display_postconditions() {
    display_conditions "Postconditions:" "$@"
}

display_conditions() {
    set_bold
    set_font_color_to ${yellow}

    local -r _message="${1}"
    readarray -td: _conditions <<< "${@}"
    set_underline_to "${_message}"
    for _condition in "${_conditions[@]:1}"; do
        echo "  ►${_condition}"
    done

    reset_to_default
}

display_test_suite_name() {
    local -r _test_suite_name="Running ${1}..."
    local -ri _test_suite_delimiter_length=$(terminal_columns_number)
    local -r _test_suite_delimiter_character='─'
    local -ri _color=${blue}

    echo
    echo
    create_banner "${_test_suite_name}" \
                  "${_test_suite_delimiter_character}" \
                  ${_test_suite_delimiter_length} \
                  ${_color}
    echo
}

create_test_banner() {
    local -ri _banner_characters_number=$(terminal_columns_number)
    local -r _banner_character='━'
    local -r _message="Running ULTaxi's tests..."
    local -ri _color=${magenta}

    create_banner "${_message}" "${_banner_character}" ${_banner_characters_number} ${_color}
}

create_banner() {
    local -r _message="${1}"
    local -r _banner_character="${2}"
    local -ri _banner_characters_number=${3}
    local -ri _color=${4}
    local -r _banner_delimiter="$(repeat_characters ${_banner_character} ${_banner_characters_number})"
    local -r _banner_message="$(center_message_horizontally_with_spaces "${_message}" \
                                                                        ${_banner_characters_number})"

    add_bold add_font_color ${_color} echo "$(surround_message_vertically_by "${_banner_message}" \
                                                                             "${_banner_delimiter}")"
}

surround_message_vertically_by() {
    local -r _message="${1}"
    local -r _delimiter="${2}"
    cat << EOF
${_delimiter}
${_message}
${_delimiter}
EOF
}

center_message_horizontally_with_spaces() {
    local -r _message="${1}"
    local -ri _message_length=${#_message}
    local -ri _x=${2}
    local -ri _padding=$(round_division "$((${_x} - ${_message_length}))" 2)
    echo "$(repeat_characters " " ${_padding})${_message}"
}

repeat_characters() {
    local -r _characters="${1}"
    local -ri _times=${2}
    printf "${_characters}%.0s" $(seq 1 ${_times})
}
