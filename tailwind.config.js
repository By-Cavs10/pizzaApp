/** @type {import('tailwindcss').Config} */

module.exports = {
  content: ["./src/main/resources/templates/*.{html,js}",
  "./src/main/resources/templates/**/*.html",
    "./src/main/resources/static/**/*.js",],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Pacifico', 'sans-serif'],
    },
  },
  },
  plugins: [
    require('@tailwindcss/forms'),
  ],
}

