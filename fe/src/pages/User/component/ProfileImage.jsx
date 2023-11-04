import defaultProfileImage from "@/assets/profile.png";

export function ProfileImage({ width, alt }) {
  return (
    <img
      src={defaultProfileImage}
      width={width || "24"}
      className="img-fluid rounded-circle shadow-sm"
      alt={alt || ""}
    />
  );
}
