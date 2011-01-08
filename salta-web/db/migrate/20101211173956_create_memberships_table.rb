class CreateMembershipsTable < ActiveRecord::Migration
  def self.up
    create_table :memberships, :id => false do |t|
      t.references :member
      t.references :group
      t.timestamps
    end
  end

  def self.down
    drop_table :memberships
  end
end
