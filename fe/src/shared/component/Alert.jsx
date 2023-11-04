export function Alert(props) {
  const { styleType, message, center } = props;
  let textCenter = "";
  if (center) {
    textCenter = "text-center";
  }
  return (
    <div className={`${textCenter} alert alert-${styleType || "success"}`}>
      {message}
    </div>
  );
}
