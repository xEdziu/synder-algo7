import {
  NavigationMenu,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  navigationMenuTriggerStyle,
} from '@/components/ui/navigation-menu';
import { ThemeToggle } from '@/components/theme';
import { cn } from '@/lib/utils';

interface NavbarProps {
  className?: string;
  userComponent?: React.ReactNode;
  isLoggedIn?: boolean;
}

export function Navbar({ className, userComponent, isLoggedIn = false }: NavbarProps) {
  return (
    <header
      className={cn(
        'sticky top-0 z-50 w-full border-b transition-colors duration-300 m-0',
        'bg-[var(--bg)] border-[var(--border)]',
        className
      )}
    >
      <div className="container flex h-16 items-center justify-between px-4 mx-auto">
        {/* Logo / Brand */}
        <div className="flex items-center gap-2">
          <a
            href={isLoggedIn ? "/dashboard" : "/"}
            className="flex items-center gap-2 no-underline hover:opacity-80 transition-opacity"
          >
            <h1 className="text-2xl font-bold m-0" style={{ color: 'var(--color-accent)' }}>
              SellHub
            </h1>
          </a>
        </div>

        {/* Navigation Menu - only show when logged in */}
        {isLoggedIn && (
          <NavigationMenu className="hidden md:flex">
            <NavigationMenuList>
            <NavigationMenuItem>
              <NavigationMenuLink
                href="/dashboard"
                className={cn(
                  navigationMenuTriggerStyle(),
                  'transition-colors duration-200',
                  'hover:bg-[var(--bg-light)] hover:text-[var(--color-accent)]'
                )}
              >
                Dashboard
              </NavigationMenuLink>
            </NavigationMenuItem>

            <NavigationMenuItem>
              <NavigationMenuLink
                href="/orders"
                className={cn(
                  navigationMenuTriggerStyle(),
                  'transition-colors duration-200',
                  'hover:bg-[var(--bg-light)] hover:text-[var(--color-accent)]'
                )}
              >
                Orders
              </NavigationMenuLink>
            </NavigationMenuItem>

            <NavigationMenuItem>
              <NavigationMenuLink
                href="/refunds"
                className={cn(
                  navigationMenuTriggerStyle(),
                  'transition-colors duration-200',
                  'hover:bg-[var(--bg-light)] hover:text-[var(--color-accent)]'
                )}
              >
                Refunds
              </NavigationMenuLink>
            </NavigationMenuItem>

            <NavigationMenuItem>
              <NavigationMenuLink
                href="/inventory"
                className={cn(
                  navigationMenuTriggerStyle(),
                  'transition-colors duration-200',
                  'hover:bg-[var(--bg-light)] hover:text-[var(--color-accent)]'
                )}
              >
                Inventory
              </NavigationMenuLink>
            </NavigationMenuItem>

            <NavigationMenuItem>
              <NavigationMenuLink
                href="/invoices"
                className={cn(
                  navigationMenuTriggerStyle(),
                  'transition-colors duration-200',
                  'hover:bg-[var(--bg-light)] hover:text-[var(--color-accent)]'
                )}
              >
                Invoices
              </NavigationMenuLink>
            </NavigationMenuItem>
          </NavigationMenuList>
        </NavigationMenu>
        )}

        {/* User Component & Theme Toggle (always theme toggle on far right) */}
        <div className="flex items-center gap-4">
          {userComponent}
          <ThemeToggle />
        </div>
      </div>
    </header>
  );
}
