# ULTaxi Test Suite

## Dependencies

The following dependencies are required:

- `bash`
- `curl`

Also, you will need a file called `email.txt` with a valid email address
inside.

## Execution

### Full Suite

To run this test suite, you can execute the following commands:

```sh
chmod +x main.bash && ./main.bash
```

or

```sh
bash main.bash
```

### Individual Suite

To run individual test suite, you can source the files and invoke the test suites directly. For example

```sh
source "drivers-test.bash"
execute_logged_as "${administrator}" it_should_add_a_driver
```

## Options

To view the full options, you can run the following command:

```sh
bash main.bash --help
```

which will display the following prompt:

```
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
```
