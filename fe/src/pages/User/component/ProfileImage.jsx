import defaultProfileImage from '@/assets/profile.png';

export function ProfileImage({ width, height, alt, tempImage, image }) {
  // proxy tanimi yapildi => vite config icerisinde
  const profileImage = image ? `/assets/profile/${image}` : defaultProfileImage;
  return (
    <img
      src={tempImage || profileImage}
      width={width || height || '24'}
      height={height || width || '24'}
      className={'rounded-circle shadow-sm'}
      alt={alt || ''}
      onError={({ target }) => {
        target.src = defaultProfileImage;
      }}
    />
  );
}
