import { getUser } from "@/pages/User/api.js";
import { Alert } from "@/shared/component/Alert.jsx";
import { useRouteParamApiRequest } from "@/shared/hooks/useRouteParamApiRequest.js";
import { Spinner } from "@/shared/component/Spinner.jsx";
import { ProfileCard } from "@/pages/User/component/ProfileCard.jsx";

export function UserPage() {
  const {
    apiProgress,
    data: user,
    error: errorMessage,
  } = useRouteParamApiRequest("userId", getUser);

  let page = "";

  if (apiProgress) {
    return (
      <Alert
        message={<Spinner big={true} />}
        center={true}
        styleType={"warning"}
      />
    );
  }
  if (user) {
    page = <ProfileCard user={user} />;
  }

  return (
    <>
      {errorMessage && (
        <Alert styleType={"danger"} message={errorMessage} center={false} />
      )}
      {page}
    </>
  );
}
