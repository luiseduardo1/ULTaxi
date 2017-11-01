#! /usr/bin/env bash

declare -xr is_math_file_loaded=true

round_division() {
    local -ri _numerator=${1}
    local -ri _denominator=${2}
    echo $(( (${_numerator} + ${_denominator} / 2) / ${_denominator} ))
}
