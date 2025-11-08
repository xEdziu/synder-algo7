import React, { useEffect, useState } from "react";

interface CssColor {
  name: string;
  value: string;
}

export default function App() {
  const [colors, setColors] = useState<CssColor[]>([]);
  const [isDark, setIsDark] = useState(false);

  useEffect(() => {
    // Pobierz wszystkie zmienne CSS z :root
    const styles = getComputedStyle(document.documentElement);
    const allVars = Array.from(styles)
      .filter((v) => v.startsWith("--"))
      .map((v) => ({
        name: v,
        value: styles.getPropertyValue(v).trim(),
      }))
      // filtrowanie tylko zmiennych kolorów
      .filter((v) => v.value.startsWith("hsl") || v.value.startsWith("oklch"));

    setColors(allVars);
  }, [isDark]);

  const toggleDark = () => {
    document.documentElement.classList.toggle("dark");
    setIsDark((prev) => !prev);
  };

  return (
    <div className="min-h-screen bg-[var(--bg)] text-[var(--text)] p-8 transition-colors duration-300">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold text-[var(--color-accent)]">
          🎨 Paleta kolorów (Tailwind + CSS Variables)
        </h1>

        <button
          onClick={toggleDark}
          className="px-4 py-2 bg-[var(--color-accent)] text-[var(--text)] rounded-md font-semibold shadow-md hover:opacity-80 transition"
        >
          {isDark ? "☀️ Jasny motyw" : "🌙 Ciemny motyw"}
        </button>
      </div>

      <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        {colors.map(({ name, value }) => (
          <div
            key={name}
            className="flex flex-col border border-[var(--border-muted)] rounded-lg overflow-hidden shadow-sm"
          >
            <div
              className="h-20 w-full"
              style={{ backgroundColor: `var(${name})` }}
            ></div>
            <div className="p-3 text-sm">
              <div className="font-semibold text-[var(--color-tertiary)]">
                {name}
              </div>
              <div className="text-[var(--text-muted)]">{value}</div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
