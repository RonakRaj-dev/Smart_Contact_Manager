console.log("Script Loaded");

let currentTheme = getTheme();

//initial -->
changeTheme(currentTheme);

function changeTheme() {
  //set to web page
  document.querySelector("html").classList.add(currentTheme);

  //set a listener to change theme button
  const changeThemeButton = document.querySelector("#theme_change_button");
  changeThemeButton.querySelector('span').textContent = currentTheme == "light" ? "Dark" : "Light";
  changeThemeButton.addEventListener("click", (event) => {
    const oldTheme = currentTheme;
    console.log("Button clicked to change the theme");
    if (currentTheme === "dark") {
      //theme to light
      currentTheme = "light";
    } else {
      //theme to dark
      currentTheme = "dark";
    }

    //localstorage update
    setTheme(currentTheme);

    //remove the current theme
    document.querySelector("html").classList.remove(oldTheme);
    //update the current theme
    document.querySelector("html").classList.add(currentTheme);

    //change the text of button 
    changeThemeButton.querySelector('span').textContent = currentTheme == "light" ? "Dark" : "Light";
  });
}

// set theme to localstorage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

// get theme from local storage
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}
