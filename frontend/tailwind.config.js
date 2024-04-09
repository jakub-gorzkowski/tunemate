/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      textColor: {
        'menu': '#AAAAAA',
        'title': '#DDDDDD'
      },
      backgroundColor: {
        'menu': '#212121',
        'menu-current': '#262626',
        'search': '#444444',
        'page': '#222222',
        'page-hover': '#303030',
        'cover-placeholder': '#D9D9D9'
      },
      borderColor: {
        'menu': '#383838'
      },
      width: {
        'search': '37.5em'
      },
      height: {
        'search': '1.875em'
      }
    },
  },
  plugins: [],
}

