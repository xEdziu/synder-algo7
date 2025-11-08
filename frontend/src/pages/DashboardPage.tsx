export function DashboardPage() {
  return (
    <div
      className="min-h-screen pt-8"
      style={{
        backgroundColor: 'var(--bg)',
      }}
    >
      <div className="container mx-auto px-4 py-8">
        <h1
          className="text-3xl font-bold mb-6"
          style={{ color: 'var(--text)' }}
        >
          Dashboard
        </h1>
        <div
          className="rounded-lg shadow-lg border p-6"
          style={{
            backgroundColor: 'var(--bg-light)',
            borderColor: 'var(--border)',
          }}
        >
          <p style={{ color: 'var(--text-muted)' }}>
            Welcome to your SellHub dashboard. More features coming soon!
          </p>
        </div>
      </div>
    </div>
  );
}
