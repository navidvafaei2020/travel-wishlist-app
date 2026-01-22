const colorMap = {
  beach: "bg-yellow-100 text-yellow-700",
  cheap: "bg-green-100 text-green-700",
  luxury: "bg-purple-100 text-purple-700",
  historical: "bg-orange-100 text-orange-700",
  nightlife: "bg-pink-100 text-pink-700",
  romantic: "bg-red-100 text-red-700",
  adventure: "bg-teal-100 text-teal-700",
  family_friendly: "bg-blue-100 text-blue-700",
  nature: "bg-green-100 text-green-800",
  shopping: "bg-indigo-100 text-indigo-700",
  cultural: "bg-yellow-100 text-yellow-800",
  mountains: "bg-gray-100 text-gray-700",
  foodie: "bg-rose-100 text-rose-700",
  ski: "bg-cyan-100 text-cyan-700",
  desert: "bg-amber-100 text-amber-700",
  island: "bg-teal-100 text-teal-800",
  wellness: "bg-lime-100 text-lime-700",
  festival: "bg-fuchsia-100 text-fuchsia-700",
  romantic_getaway: "bg-red-100 text-red-800",
  eco_friendly: "bg-green-50 text-green-900",
  art_scene: "bg-violet-100 text-violet-700",
  historic_architecture: "bg-orange-50 text-orange-900",
  water_sports: "bg-blue-50 text-blue-800",
  wildlife: "bg-emerald-100 text-emerald-800",
  hiking: "bg-lime-50 text-lime-800",
  offbeat: "bg-stone-100 text-stone-700",
  photography: "bg-pink-50 text-pink-800"  
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
