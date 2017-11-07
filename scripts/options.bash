#! /usr/bin/env bash

[ -z ${is_decorations_file_loaded} ] && source "decorations.bash"

declare -xr is_options_file_loaded=true
declare -x output_file
declare -x keep

display_help() {
    cat << 'EOF'
Usage: bash main.bash [OPTION]...
Execute the tests of ULTaxi.
Example: bash main.bash --output-file="tests_results.log" --keep

Output control:
  --output-file=FILE    direct all the output to FILE
  --keep                when `--keep` is used along with `--output-file`, then
                        instead of capturing all output, output will be written
                        to FILE and to stdout

Miscellaneous:
  -h, --help            display this help text and exit
EOF
}

parse_command_line_arguments() {
    for option in "${@}"; do
        case "${option}" in
            --output-file=*)
                output_file="${option#*=}"
                shift
                ;;
            -k|--keep)
                keep=true
                shift
                ;;
            -h|--help)
                display_help
                exit 1
                ;;
            *)
                display_help
                exit 1
                ;;
        esac
    done
}

