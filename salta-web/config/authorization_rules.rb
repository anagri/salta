authorization do
  role :authenticated_user do
    has_permission_on :dashboard, :to => [:index]
  end

  role :admin do
    include :authenticated_user
  end
end
