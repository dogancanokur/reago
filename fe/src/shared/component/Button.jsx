import { Spinner } from "@/shared/component/Spinner.jsx";

export function Button({
  disabled,
  type,
  className = "btn-primary",
  text,
  apiProgress,
  onClick,
}) {
  return (
    <button
      disabled={disabled}
      type={type || "submit"}
      onClick={onClick}
      className={"btn " + className}
    >
      {apiProgress && <Spinner />} {text}
    </button>
  );
}
