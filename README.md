ColorTransitionDrawable
===============

This library provides a variant of the `Drawable` class which supports color transition animation. Works on API level 9 or later.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.h6ah4i.android.colortransitiondrawable/colortransitiondrawable/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.h6ah4i.android.colortransitiondrawable/colortransitiondrawable)

Target platforms
---

- API level 9 or later


Latest version
---

- Version 0.5.2  (April 30, 2015)

Getting started
---

This library is published on Maven Central. Just add these lines to `build.gradle`.

```groovy
dependencies {
    compile 'com.h6ah4i.android.colortransitiondrawable:colortransitiondrawable:0.5.2'
}
```

Usage
---

### Java code

```java
ColorTransitionDrawable drawable = new ColorTransitionDrawable();

view.setBackground(drawable);

// [Optional] set default transition animation duration (default: 300 ms)
drawable.setDuration(1000);

// [Optional] set interpolator
drawable.setInterpolator(new AccelerateDecelerateInterpolator());


// set color with animation (use default duration)
drawable.setColor(Color.BLACK);

// set color without animation
drawable.setColor(Color.BLACK, false);

// set color with animation (specified duration)
drawable.setColor(Color.BLACK, 500);

```

License
---

This library is licensed under the [Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

See [`LICENSE`](LICENSE) for full of the license text.

    Copyright (C) 2015 Haruki Hasegawa

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
