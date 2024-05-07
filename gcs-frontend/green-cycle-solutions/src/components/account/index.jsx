import React, { useContext } from "react";
import Avatar from "../../assets/images/Avatar.png";
import Avatar2 from "../../assets/images/Avatar2.png";
import AuthContext from "../../api/AuthProvider";

function Account() {
  const { auth } = useContext(AuthContext);

  return (
    <div className="container mt-5 pt-5">
      <div className="row">
        <div className="col-4">
          {auth.gender && auth.gender === "FEMALE" ? (
            <div>
              <img
                className="rounded-circle"
                alt=""
                src={Avatar2}
                width="250"
                height="250"
                border-radius="20%"
              ></img>
            </div>
          ) : (
            <div>
              <img
                className="rounded-circle"
                //   style={{ height: "auto" }}
                alt=""
                src={Avatar}
                width="250"
                height="250"
                border-radius="20%"
              ></img>
            </div>
          )}
        </div>
        <div className="col-8">Column</div>
      </div>
    </div>
  );
}

export default Account;
