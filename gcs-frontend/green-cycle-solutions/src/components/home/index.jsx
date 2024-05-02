import { useContext } from "react";
import AuthContext from "../../api/AuthProvider";

function Home() {
  const { auth } = useContext(AuthContext);

  return (
    <div>
      Here is Home
      {auth.user ? (
        <p>
          Hello {auth.user} {auth.role}
        </p>
      ) : null}
    </div>
  );
}

export default Home;
