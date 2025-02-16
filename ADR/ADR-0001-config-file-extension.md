Separation of .properties and .yaml were initially made for conceal secret-key in .gitignore and expose rest of standard configuration. However since this is purely demonstrional example both were included, user should replace secret-key for use.

Since this separation is not essential now I would stay with .yaml configuration as it offers more readability with larger amount of specified settings.