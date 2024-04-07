/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      textColor: {
        'menu': '#AAAAAA'
      },
      backgroundColor: {
        'menu': '#212121',
        'menu-current': '#262626'
      },
      borderColor: {
        'menu': '#383838'
      }
    },
  },
  plugins: [],
}

