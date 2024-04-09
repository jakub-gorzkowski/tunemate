import Navigation from "./component/menu/Navigation";
import HomePage from "./component/page/HomePage";

function App() {
  return (
      <>
          <div className={"flex flex-auto"}>
              <Navigation/>
              <HomePage/>
          </div>
      </>
  );
}

export default App;
