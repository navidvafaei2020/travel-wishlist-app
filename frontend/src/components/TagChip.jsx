const colorMap = {
  beach: "bg-yellow-100 text-yellow-700",
  cheap: "bg-green-100 text-green-700",
  luxury: "bg-purple-100 text-purple-700",
};

const TagChip = ({ label }) => {
  const key = label.toLowerCase();

  const colorClass =
    colorMap[key] || "bg-gray-100 text-gray-700";

  return (
    <span
      className={`text-xs font-medium px-2 py-1 rounded-full ${colorClass}`}
    >
      {label}
    </span>
  );
};

export default TagChip;
