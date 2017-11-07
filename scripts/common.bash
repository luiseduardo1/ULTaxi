#! /usr/bin/env bash

[ -z ${is_utils_file_loaded} ] && source "utils.bash"

declare -xr is_common_file_loaded=true

declare -xr base_url="http://localhost:8080"
declare -xr json_content_type_header="Content-Type: application/json"
declare -xr json_accept_header="Accept: application/json"
declare -xr curl_configuration_file=".curlrc"

declare -xr default_user="$(create_user "clientUsername" \
                                        "clientPassword" \
                                        "client@ultaxi.ca")"
declare -xr default_driver="$(create_user "driverUsername" \
                                          "driverPassword" \
                                          "driver@ultaxi.ca")"

declare -xr username="john_smith"
declare -xr updated_username="john_smith_2"
declare -xr password="hunter2"
declare -x email="$(cat "email.txt")"
declare -xr user="$(create_user "${username}" \
                                "${password}" \
                                "${email}")"

declare -xr driver_username="jane_doe"
declare -xr driver_last_name="Doe"
declare -xr driver_first_name="Jane"
declare -xr social_insurance_number="046454286"
declare -xr phone_number="2342355678"
declare -xr driver="$(create_driver "${driver_username}" \
                                    "${password}" \
                                    "${email}" \
                                    "${social_insurance_number}" \
                                    "${phone_number}" \
                                    "${driver_last_name}" \
                                    "${driver_first_name}")"

declare -xr administrator_username="administratorUsername"
declare -xr administrator_password="administratorPassword"
declare -xr administrator_email="administrator@ultaxi.ca"
declare -xr administrator="$(create_user "${administrator_username}" \
                                         "${administrator_password}" \
                                         "${administrator_email}")"

declare -xr car_vehicle_type="CAR"
declare -xr vehicle_color="Metallic Blue"
declare -xr vehicle_model="Honda Civic Si"
declare -xr registration_number="T33333"
declare -xr car_vehicle="$(create_vehicle "${car_vehicle_type}" \
                                          "${vehicle_color}" \
                                          "${vehicle_model}" \
                                          "${registration_number}")"
declare -xr vehicle_association="$(create_vehicle_association "${registration_number}" \
                                                              "${driver_username}")"

declare -xr latitude_starting_position="25.5"
declare -xr longitude_starting_position="26.0"
declare -xr note="Note"
declare -xr transport_request="$(create_transport_request ${latitude_starting_position} \
                                                          ${longitude_starting_position} \
                                                          "${car_vehicle_type}" \
                                                          "${username}" \
                                                          "${note}")"
