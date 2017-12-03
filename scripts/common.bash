#! /usr/bin/env bash

[ -z ${is_utils_file_loaded} ] && source "utils.bash"

declare -xr is_common_file_loaded=true

declare -xr base_url="http://localhost:8080"
declare -xr json_content_type_header="Content-Type: application/json"
declare -xr json_accept_header="Accept: application/json"
declare -xr plain_text_content_type_header="Content-Type: text/plain"
declare -xr curl_configuration_file=".curlrc"

declare -xr default_user="$(create_authentication_credentials "clientUsername" \
                                                           "clientPassword")"
declare -xr default_driver="$(create_authentication_credentials "driverUsername" \
                                                             "driverPassword")"

declare -xr client_username="john_smith"
declare -xr client_updated_username="john_smith_2"
declare -xr password="hunter2"
declare -x email_address="$(cat "email.txt")"
declare -xr phone_number="2342355678"
declare -xr client="$(create_client "${client_username}" \
                                         "${password}" \
                                         "${email_address}" \
                                         "${phone_number}")"

declare -xr driver_username="jane_doe"
declare -xr driver_last_name="Doe"
declare -xr driver_first_name="Jane"
declare -xr social_insurance_number="046454286"
declare -xr driver="$(create_driver "${driver_username}" \
                                    "${password}" \
                                    "${email_address}" \
                                    "${social_insurance_number}" \
                                    "${phone_number}" \
                                    "${driver_last_name}" \
                                    "${driver_first_name}")"

declare -xr administrator_username="administratorUsername"
declare -xr administrator_password="administratorPassword"
declare -xr administrator="$(create_authentication_credentials "${administrator_username}" \
                                                            "${administrator_password}")"

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
                                                          "${client_username}" \
                                                          "${note}")"
