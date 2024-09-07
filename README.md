DotsIndicatorView Library

DotsIndicatorView is a customizable dots indicator view for ViewPager2 and RecyclerView. It allows developers to easily display dot indicators with support for different shapes (circle/square), custom drawables, and colors for selected and unselected dots.

Features

Supports both ViewPager2 and RecyclerView.

Customizable dot size and spacing.

Choose between circle or square shapes for dots.

Ability to use custom drawables or colors for selected and unselected dots.

Easy to integrate and fully customizable through XML attributes.

Integration

1. Add to your project

Include the DotsIndicatorView library in your project. If it's hosted on a Maven repository or a local module, add the corresponding dependency:

Copy code


dependencies {

		maven { url 'https://jitpack.io' }

    implementation 'com.codetech.lib:dotsindicatorview:1.0.0'

}

2. Using DotsIndicatorView in XML

You can define the DotsIndicatorView directly in your XML layout and customize it using the available attributes:

xml
Copy code

<com.codetech.lib.DotsIndicatorView
    android:id="@+id/dotsIndicator"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:dotSize="16dp"
    app:dotSpacing="8dp"
    app:selectedDotDrawable="@drawable/selected_dot"   <!-- Optional: Custom drawable for selected dot -->
    app:unselectedDotDrawable="@drawable/unselected_dot" <!-- Optional: Custom drawable for unselected dot -->
    app:selectedDotColor="@color/black"                <!-- Optional: Color for selected dot -->
    app:unselectedDotColor="@color/gray"               <!-- Optional: Color for unselected dot -->
    app:dotIsCircle="true" />                          <!-- Set true for circle shape, false for square -->

3. Attaching to ViewPager2

To use DotsIndicatorView with ViewPager2, simply call the attachViewPager method and pass in your ViewPager2 instance:

Copy code

val viewPager = findViewById<ViewPager2>(R.id.viewPager)

val dotsIndicator = findViewById<DotsIndicatorView>(R.id.dotsIndicator)

dotsIndicator.attachViewPager(viewPager)

4. Attaching to RecyclerView

For RecyclerView with paging logic, attach the DotsIndicatorView like this:

kotlin

val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

val dotsIndicator = findViewById<DotsIndicatorView>(R.id.dotsIndicator)

dotsIndicator.attachRecyclerView(recyclerView)

5. Customizing the Dots

You can customize the appearance of the dots by using either drawables or colors.

Custom Drawables

You can set custom drawables for the selected and unselected states:

Copy code

app:selectedDotDrawable="@drawable/custom_selected"
app:unselectedDotDrawable="@drawable/custom_unselected"

Custom Colors

If you want to use colors instead of drawables, just set selectedDotColor and unselectedDotColor:

xml
Copy code

app:selectedDotColor="@color/selected_color"
app:unselectedDotColor="@color/unselected_color"
Dot Shape
Choose between circle or square shapes for the dots by setting the dotIsCircle attribute:

xml
Copy code

app:dotIsCircle="true"  <!-- Circle (default) -->
app:dotIsCircle="false" <!-- Square -->
6. Customization Options
Dot Size: Adjust the size of each dot using app:dotSize.
Dot Spacing: Set the space between dots using app:dotSpacing.
Attributes

Attribute	Description	Default Value
app:dotSize	Size of the dots (in dp).	16dp
app:dotSpacing	Space between the dots (in dp).	8dp
app:selectedDotDrawable	Drawable for the selected dot.	None
app:unselectedDotDrawable	Drawable for the unselected dot.	None
app:selectedDotColor	Color for the selected dot (fallback).	White
app:unselectedDotColor	Color for the unselected dot (fallback).	Black
app:dotIsCircle	Whether the dot is a circle or square.	true (circle)

License
MIT License
