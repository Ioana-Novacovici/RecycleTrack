import { useContext } from "react";
import AuthContext from "../../api/AuthProvider";

function Home() {
  const { auth } = useContext(AuthContext);

  return (
    <div>
      Here is Home
      {auth.username ? (
        <p>
          Hello {auth.username} {auth.role}
        </p>
      ) : null}
    </div>
  );
}

export default Home;
